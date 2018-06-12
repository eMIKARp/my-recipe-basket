package pl.mojprzepisnik.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Vote {
    
    private long id;
    private long user_id;
    private long recipe_id;
    private Timestamp timestamp;
    private VoteType voteType;

    public Vote() {
    }

    public Vote(long id, long user_id, long recipe_id, Timestamp timestamp, VoteType voteType) {
        this.id = id;
        this.user_id = user_id;
        this.recipe_id = recipe_id;
        this.timestamp = timestamp;
        this.voteType = voteType;
    }
    
    public Vote(Vote vote) {
        this.id = vote.id;
        this.user_id = vote.user_id;
        this.recipe_id = vote.recipe_id;
        this.timestamp = new Timestamp(vote.timestamp.getTime());
        this.voteType = vote.voteType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    @Override
    public String toString() {
        return "Vote{" + "id=" + id + ", user_id=" + user_id + ", recipe_id=" + recipe_id + ", timestamp=" + timestamp + ", voteType=" + voteType + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + (int) (this.user_id ^ (this.user_id >>> 32));
        hash = 79 * hash + (int) (this.recipe_id ^ (this.recipe_id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.timestamp);
        hash = 79 * hash + Objects.hashCode(this.voteType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vote other = (Vote) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.recipe_id != other.recipe_id) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        if (this.voteType != other.voteType) {
            return false;
        }
        return true;
    }
    
}
