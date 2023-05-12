/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

/**
 * TeamMemberDTO
 */
public class TeamMemberDTO {
  @SerializedName("bonusPenalty")
  private Integer bonusPenalty = null;

  @SerializedName("idTeam")
  private Integer idTeam = null;

  @SerializedName("individualMark")
  private Integer individualMark = null;

  @SerializedName("team")
  private TeamDTO team = null;

  @SerializedName("user")
  private UserDTO user = null;

  public TeamMemberDTO bonusPenalty(Integer bonusPenalty) {
    this.bonusPenalty = bonusPenalty;
    return this;
  }

   /**
   * Get bonusPenalty
   * @return bonusPenalty
  **/
  @ApiModelProperty(value = "")
  public Integer getBonusPenalty() {
    return bonusPenalty;
  }

  public void setBonusPenalty(Integer bonusPenalty) {
    this.bonusPenalty = bonusPenalty;
  }

  public TeamMemberDTO idTeam(Integer idTeam) {
    this.idTeam = idTeam;
    return this;
  }

   /**
   * Get idTeam
   * @return idTeam
  **/
  @ApiModelProperty(value = "")
  public Integer getIdTeam() {
    return idTeam;
  }

  public void setIdTeam(Integer idTeam) {
    this.idTeam = idTeam;
  }

  public TeamMemberDTO individualMark(Integer individualMark) {
    this.individualMark = individualMark;
    return this;
  }

   /**
   * Get individualMark
   * @return individualMark
  **/
  @ApiModelProperty(value = "")
  public Integer getIndividualMark() {
    return individualMark;
  }

  public void setIndividualMark(Integer individualMark) {
    this.individualMark = individualMark;
  }

  public TeamMemberDTO team(TeamDTO team) {
    this.team = team;
    return this;
  }

   /**
   * Get team
   * @return team
  **/
  @ApiModelProperty(value = "")
  public TeamDTO getTeam() {
    return team;
  }

  public void setTeam(TeamDTO team) {
    this.team = team;
  }

  public TeamMemberDTO user(UserDTO user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")
  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamMemberDTO teamMemberDTO = (TeamMemberDTO) o;
    return Objects.equals(this.bonusPenalty, teamMemberDTO.bonusPenalty) &&
        Objects.equals(this.idTeam, teamMemberDTO.idTeam) &&
        Objects.equals(this.individualMark, teamMemberDTO.individualMark) &&
        Objects.equals(this.team, teamMemberDTO.team) &&
        Objects.equals(this.user, teamMemberDTO.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bonusPenalty, idTeam, individualMark, team, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamMemberDTO {\n");
    
    sb.append("    bonusPenalty: ").append(toIndentedString(bonusPenalty)).append("\n");
    sb.append("    idTeam: ").append(toIndentedString(idTeam)).append("\n");
    sb.append("    individualMark: ").append(toIndentedString(individualMark)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

