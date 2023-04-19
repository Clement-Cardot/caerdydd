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
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.ProjectDTO;
import io.swagger.client.model.TeamMemberDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TeamDTO
 */
public class TeamDTO {
  @SerializedName("filePathFinalScopeStatement")
  private String filePathFinalScopeStatement = null;

  @SerializedName("filePathReport")
  private String filePathReport = null;

  @SerializedName("filePathScopeStatement")
  private String filePathScopeStatement = null;

  @SerializedName("filePathScopeStatementAnalysis")
  private String filePathScopeStatementAnalysis = null;

  @SerializedName("idTeam")
  private Integer idTeam = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("projectDev")
  private ProjectDTO projectDev = null;

  @SerializedName("projectValidation")
  private ProjectDTO projectValidation = null;

  @SerializedName("teamMembers")
  private List<TeamMemberDTO> teamMembers = null;

  @SerializedName("teamValidationMark")
  private Integer teamValidationMark = null;

  @SerializedName("teamWorkMark")
  private Integer teamWorkMark = null;

  @SerializedName("testBookLink")
  private String testBookLink = null;

  public TeamDTO filePathFinalScopeStatement(String filePathFinalScopeStatement) {
    this.filePathFinalScopeStatement = filePathFinalScopeStatement;
    return this;
  }

   /**
   * Get filePathFinalScopeStatement
   * @return filePathFinalScopeStatement
  **/
  @ApiModelProperty(value = "")
  public String getFilePathFinalScopeStatement() {
    return filePathFinalScopeStatement;
  }

  public void setFilePathFinalScopeStatement(String filePathFinalScopeStatement) {
    this.filePathFinalScopeStatement = filePathFinalScopeStatement;
  }

  public TeamDTO filePathReport(String filePathReport) {
    this.filePathReport = filePathReport;
    return this;
  }

   /**
   * Get filePathReport
   * @return filePathReport
  **/
  @ApiModelProperty(value = "")
  public String getFilePathReport() {
    return filePathReport;
  }

  public void setFilePathReport(String filePathReport) {
    this.filePathReport = filePathReport;
  }

  public TeamDTO filePathScopeStatement(String filePathScopeStatement) {
    this.filePathScopeStatement = filePathScopeStatement;
    return this;
  }

   /**
   * Get filePathScopeStatement
   * @return filePathScopeStatement
  **/
  @ApiModelProperty(value = "")
  public String getFilePathScopeStatement() {
    return filePathScopeStatement;
  }

  public void setFilePathScopeStatement(String filePathScopeStatement) {
    this.filePathScopeStatement = filePathScopeStatement;
  }

  public TeamDTO filePathScopeStatementAnalysis(String filePathScopeStatementAnalysis) {
    this.filePathScopeStatementAnalysis = filePathScopeStatementAnalysis;
    return this;
  }

   /**
   * Get filePathScopeStatementAnalysis
   * @return filePathScopeStatementAnalysis
  **/
  @ApiModelProperty(value = "")
  public String getFilePathScopeStatementAnalysis() {
    return filePathScopeStatementAnalysis;
  }

  public void setFilePathScopeStatementAnalysis(String filePathScopeStatementAnalysis) {
    this.filePathScopeStatementAnalysis = filePathScopeStatementAnalysis;
  }

  public TeamDTO idTeam(Integer idTeam) {
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

  public TeamDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TeamDTO projectDev(ProjectDTO projectDev) {
    this.projectDev = projectDev;
    return this;
  }

   /**
   * Get projectDev
   * @return projectDev
  **/
  @ApiModelProperty(value = "")
  public ProjectDTO getProjectDev() {
    return projectDev;
  }

  public void setProjectDev(ProjectDTO projectDev) {
    this.projectDev = projectDev;
  }

  public TeamDTO projectValidation(ProjectDTO projectValidation) {
    this.projectValidation = projectValidation;
    return this;
  }

   /**
   * Get projectValidation
   * @return projectValidation
  **/
  @ApiModelProperty(value = "")
  public ProjectDTO getProjectValidation() {
    return projectValidation;
  }

  public void setProjectValidation(ProjectDTO projectValidation) {
    this.projectValidation = projectValidation;
  }

  public TeamDTO teamMembers(List<TeamMemberDTO> teamMembers) {
    this.teamMembers = teamMembers;
    return this;
  }

  public TeamDTO addTeamMembersItem(TeamMemberDTO teamMembersItem) {
    if (this.teamMembers == null) {
      this.teamMembers = new ArrayList<TeamMemberDTO>();
    }
    this.teamMembers.add(teamMembersItem);
    return this;
  }

   /**
   * Get teamMembers
   * @return teamMembers
  **/
  @ApiModelProperty(value = "")
  public List<TeamMemberDTO> getTeamMembers() {
    return teamMembers;
  }

  public void setTeamMembers(List<TeamMemberDTO> teamMembers) {
    this.teamMembers = teamMembers;
  }

  public TeamDTO teamValidationMark(Integer teamValidationMark) {
    this.teamValidationMark = teamValidationMark;
    return this;
  }

   /**
   * Get teamValidationMark
   * @return teamValidationMark
  **/
  @ApiModelProperty(value = "")
  public Integer getTeamValidationMark() {
    return teamValidationMark;
  }

  public void setTeamValidationMark(Integer teamValidationMark) {
    this.teamValidationMark = teamValidationMark;
  }

  public TeamDTO teamWorkMark(Integer teamWorkMark) {
    this.teamWorkMark = teamWorkMark;
    return this;
  }

   /**
   * Get teamWorkMark
   * @return teamWorkMark
  **/
  @ApiModelProperty(value = "")
  public Integer getTeamWorkMark() {
    return teamWorkMark;
  }

  public void setTeamWorkMark(Integer teamWorkMark) {
    this.teamWorkMark = teamWorkMark;
  }

  public TeamDTO testBookLink(String testBookLink) {
    this.testBookLink = testBookLink;
    return this;
  }

   /**
   * Get testBookLink
   * @return testBookLink
  **/
  @ApiModelProperty(value = "")
  public String getTestBookLink() {
    return testBookLink;
  }

  public void setTestBookLink(String testBookLink) {
    this.testBookLink = testBookLink;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamDTO teamDTO = (TeamDTO) o;
    return Objects.equals(this.filePathFinalScopeStatement, teamDTO.filePathFinalScopeStatement) &&
        Objects.equals(this.filePathReport, teamDTO.filePathReport) &&
        Objects.equals(this.filePathScopeStatement, teamDTO.filePathScopeStatement) &&
        Objects.equals(this.filePathScopeStatementAnalysis, teamDTO.filePathScopeStatementAnalysis) &&
        Objects.equals(this.idTeam, teamDTO.idTeam) &&
        Objects.equals(this.name, teamDTO.name) &&
        Objects.equals(this.projectDev, teamDTO.projectDev) &&
        Objects.equals(this.projectValidation, teamDTO.projectValidation) &&
        Objects.equals(this.teamMembers, teamDTO.teamMembers) &&
        Objects.equals(this.teamValidationMark, teamDTO.teamValidationMark) &&
        Objects.equals(this.teamWorkMark, teamDTO.teamWorkMark) &&
        Objects.equals(this.testBookLink, teamDTO.testBookLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filePathFinalScopeStatement, filePathReport, filePathScopeStatement, filePathScopeStatementAnalysis, idTeam, name, projectDev, projectValidation, teamMembers, teamValidationMark, teamWorkMark, testBookLink);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamDTO {\n");
    
    sb.append("    filePathFinalScopeStatement: ").append(toIndentedString(filePathFinalScopeStatement)).append("\n");
    sb.append("    filePathReport: ").append(toIndentedString(filePathReport)).append("\n");
    sb.append("    filePathScopeStatement: ").append(toIndentedString(filePathScopeStatement)).append("\n");
    sb.append("    filePathScopeStatementAnalysis: ").append(toIndentedString(filePathScopeStatementAnalysis)).append("\n");
    sb.append("    idTeam: ").append(toIndentedString(idTeam)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    projectDev: ").append(toIndentedString(projectDev)).append("\n");
    sb.append("    projectValidation: ").append(toIndentedString(projectValidation)).append("\n");
    sb.append("    teamMembers: ").append(toIndentedString(teamMembers)).append("\n");
    sb.append("    teamValidationMark: ").append(toIndentedString(teamValidationMark)).append("\n");
    sb.append("    teamWorkMark: ").append(toIndentedString(teamWorkMark)).append("\n");
    sb.append("    testBookLink: ").append(toIndentedString(testBookLink)).append("\n");
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
