package acmcsus.debugjudge.model;

import acmcsus.debugjudge.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "problems", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ref_id", "competition_id"})
})
public class Problem extends Model {
    
    public static final Finder<Long, Problem> find = new Finder<>(Problem.class);
    
    @Id
    public Long id;
    
    @Column(name = "ref_id", nullable = false)
    public String refId;
    
    @Column(name = "title", nullable = false, length = 50)
    public String title;
    
    @Column(name = "description", nullable = false, columnDefinition = "BLOB")
    public String description;
    
    @Column(name = "precode", nullable = false, columnDefinition = "BLOB")
    public String precode;
    
    @Column(name = "code", nullable = false, columnDefinition = "BLOB")
    public String code;
    
    @Column(name = "postcode", nullable = false, columnDefinition = "BLOB")
    public String postcode;
    
    @JsonView(Views.JudgeView.class)
    @Column(name = "answer", nullable = false, columnDefinition = "BLOB")
    public String answer;
    
    @ManyToOne(optional = false)
    public Competition competition;
    
}
