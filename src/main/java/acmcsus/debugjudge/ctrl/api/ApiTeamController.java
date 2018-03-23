package acmcsus.debugjudge.ctrl.api;

import static acmcsus.debugjudge.ctrl.CompetitionController.getCompetitionState;
import static acmcsus.debugjudge.ctrl.api.ApiBaseController.writeForTeam;
import static java.lang.String.format;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import acmcsus.debugjudge.ProcessBody;
import acmcsus.debugjudge.ctrl.FileStore;
import acmcsus.debugjudge.ctrl.SecurityApi;
import acmcsus.debugjudge.model.Profile;
import acmcsus.debugjudge.model.Submission;
import acmcsus.debugjudge.proto.Competition.CompetitionState;
import acmcsus.debugjudge.ws.JudgeQueueHandler;
import acmcsus.debugjudge.ws.SocketHandler;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.time.Instant;
import spark.Request;
import spark.Response;
import spark.Spark;

class ApiTeamController {

  private ApiTeamController() { /* Static */ }

  static void teamApiPath() {
    Spark.before("/*", SecurityApi::teamFilter);
    get("/submissions", ApiTeamController::getSubmissions);
    post("/submissions", ApiTeamController::newSubmission);
  }


  private static String getSubmissions(Request req, Response res) throws IOException {
    Profile profile = SecurityApi.getProfile(req);
    if (profile == null) { throw halt(401); }

    return writeForTeam(FileStore.getSubmissionsForTeam(profile));
  }

  private static String newSubmission(Request req, Response res) {
    if (getCompetitionState() != CompetitionState.STARTED) {
      SocketHandler.alert(SecurityApi.getProfile(req), "Can't submit right now!");
      throw halt(400);
    }

    try {
      JsonNode json = ProcessBody.asJson(req);

      Submission submission = new Submission();
      submission.teamId = req.attribute("profile");
      submission.problemId = json.get("problem_id").asLong();
      submission.submittedAt = Date.from(Instant.now());
      submission.code = json.get("code").asText();

      Profile team = FileStore.getProfile(submission.teamId);

      FileStore.saveSubmission(submission);

      JudgeQueueHandler.getInstance().submitted(submission);

      return Long.toString(submission.id);
    } catch (Exception e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));

      throw halt(400, format("{\"error\":\"%s\"}",
        sw.toString()
          .replaceAll("\"", "\\\"")
          .replaceAll("\\\\", "\\\\")));
    }
  }

}