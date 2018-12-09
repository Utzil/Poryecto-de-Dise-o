package POJO;

public class Candidate {

    private int IdCandidate;
    private String FirstName;//
    private String MiddleName;//segundo nombre
    private String LastName;//primer apallido
    private String MotherLastName;//segundo apellido
    private int NumVotes;

    public Candidate() {
        this.IdCandidate = -1;
        this.FirstName = "";
        this.MiddleName = "";
        this.LastName = "";
        this.MotherLastName = "";
        this.NumVotes = 0;
    }

    public Candidate(int idCandidate, String firstName, String middleName, String lastName, String motherLastName, int numVotes) {
        this.IdCandidate = idCandidate;
        this.FirstName = firstName;
        this.MiddleName = middleName;
        this.LastName = lastName;
        this.MotherLastName = motherLastName;
        this.NumVotes = numVotes;
    }

    public int getIdCandidate() {
        return IdCandidate;
    }

    public void setIdCandidate(int IdCandidate) {
        this.IdCandidate = IdCandidate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMotherLastName() {
        return MotherLastName;
    }

    public void setMotherLastName(String MotherLastName) {
        this.MotherLastName = MotherLastName;
    }

    public int getNumVotes() {
        return NumVotes;
    }

    public void setNumVotes(int NumVotes) {
        this.NumVotes = NumVotes;
    }
}
