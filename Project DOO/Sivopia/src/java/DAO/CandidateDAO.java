package DAO;

import POJO.Candidate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CandidateDAO {

    private Candidate candidate = null;

    private String[] querys = {
        "INSERT INTO tblCandidate (FIRSTNAME, MIDDLENAME, LASTNAME, MOTHERLASTNAME, NUMVOTES) VALUES (?, ?, ?, ?, ?)",
        "SELECT * FROM tblCandidate",
        "SELECT * FROM tblCandidate WHERE IdCandidate = ",
        "UPDATE tblCandidate SET NUMVOTES = ? WHERE IdCandidate = ?",
        "SELECT * FROM tblCandidate WHERE NUMVOTES = (SELECT MAX(NUMVOTES) FROM tblCandidate)",
        "SELECT SUM(NUMVOTES) FROM tblCandidate"
    };

    public CandidateDAO() {
        this.candidate = new Candidate();
    }

    public boolean create()
            throws Exception {
        boolean created = false;
        try {
            HashMap hmap = new HashMap();
            hmap.put(1, getCandidate().getFirstName());
            hmap.put(2, getCandidate().getMiddleName());
            hmap.put(3, getCandidate().getLastName());
            hmap.put(4, getCandidate().getMotherLastName());
            hmap.put(5, getCandidate().getNumVotes());
            created = jdbc.getInstance().createOrUpdate(querys[0], hmap);
        } catch (Exception ex) {
            throw ex;
        }
        return created;
    }

    public List<Candidate> readAll()
            throws Exception {
        List<Candidate> candidateList = new ArrayList<Candidate>();
        try {
            ResultSet rs = jdbc.getInstance().read(querys[1]);
            while (rs.next()) {
                Candidate c = new Candidate();
                c.setIdCandidate(rs.getInt("IdCandidate"));
                c.setFirstName(rs.getString("FirstName"));
                c.setMiddleName(rs.getString("MiddleName"));
                c.setLastName(rs.getString("LastName"));
                c.setMotherLastName(rs.getString("MotherLastName"));
                c.setNumVotes(rs.getInt("NumVotes"));
                candidateList.add(c);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return candidateList;
    }

    public Candidate read(int idCandidate)
            throws Exception {
        Candidate c = null;
        try {
            String queryWithParams = querys[2] + idCandidate;
            ResultSet rs = jdbc.getInstance().read(queryWithParams);
            if (rs.next()) {
                c = new Candidate();
                c.setIdCandidate(rs.getInt("IdCandidate"));
                c.setFirstName(rs.getString("FirstName"));
                c.setMiddleName(rs.getString("MiddleName"));
                c.setLastName(rs.getString("LastName"));
                c.setMotherLastName(rs.getString("MotherLastName"));
                c.setNumVotes(rs.getInt("NumVotes"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return c;
    }
    
    public List<Candidate> highestNumVotes() 
        throws Exception {
        List<Candidate> candidateList = new ArrayList<Candidate>();
        try {
            ResultSet rs = jdbc.getInstance().read(querys[4]);
            while (rs.next()) {
                Candidate c = new Candidate();
                c.setIdCandidate(rs.getInt("IdCandidate"));
                c.setFirstName(rs.getString("FirstName"));
                c.setMiddleName(rs.getString("MiddleName"));
                c.setLastName(rs.getString("LastName"));
                c.setMotherLastName(rs.getString("MotherLastName"));
                c.setNumVotes(rs.getInt("NumVotes"));
                candidateList.add(c);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return candidateList;
    }
    
    public int getTotalVotes()
        throws Exception {
        int numVotes = 0;
        try {
            String query = querys[5];
            ResultSet rs = jdbc.getInstance().read(query);
            if (rs.next()) {
                numVotes = rs.getInt(1);
            }
        } catch (Exception ex) {
            throw ex;
        }
        
        return numVotes;
    }

    public boolean update()
            throws Exception {
        boolean updated = false;
        try {
            HashMap hmap = new HashMap();
            hmap.put(1, getCandidate().getNumVotes());
            hmap.put(2, getCandidate().getIdCandidate());
            updated = jdbc.getInstance().createOrUpdate(querys[3], hmap);
        } catch (Exception ex) {
            throw ex;
        }
        return updated;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

}
