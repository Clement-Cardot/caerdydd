# TeamControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**applyInATeamUsingPUT**](TeamControllerApi.md#applyInATeamUsingPUT) | **PUT** /api/teams/{idTeam}/{idUser} | applyInATeam
[**createTeamsUsingPUT**](TeamControllerApi.md#createTeamsUsingPUT) | **PUT** /api/teams/{nbTeams} | createTeams
[**getAllTeamMembersOfTeamByIdUsingGET**](TeamControllerApi.md#getAllTeamMembersOfTeamByIdUsingGET) | **GET** /api/teams/{idTeam}/teamMembers | getAllTeamMembersOfTeamById
[**getAllTeamsUsingGET**](TeamControllerApi.md#getAllTeamsUsingGET) | **GET** /api/teams | getAllTeams
[**getTeamByIdUsingGET**](TeamControllerApi.md#getTeamByIdUsingGET) | **GET** /api/teams/{idTeam} | getTeamById


<a name="applyInATeamUsingPUT"></a>
# **applyInATeamUsingPUT**
> UserDTO applyInATeamUsingPUT(idTeam, idUser)

applyInATeam

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamControllerApi;


TeamControllerApi apiInstance = new TeamControllerApi();
Integer idTeam = 56; // Integer | idTeam
Integer idUser = 56; // Integer | idUser
try {
    UserDTO result = apiInstance.applyInATeamUsingPUT(idTeam, idUser);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamControllerApi#applyInATeamUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idTeam** | **Integer**| idTeam |
 **idUser** | **Integer**| idUser |

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="createTeamsUsingPUT"></a>
# **createTeamsUsingPUT**
> List&lt;TeamDTO&gt; createTeamsUsingPUT(nbTeams)

createTeams

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamControllerApi;


TeamControllerApi apiInstance = new TeamControllerApi();
Integer nbTeams = 56; // Integer | nbTeams
try {
    List<TeamDTO> result = apiInstance.createTeamsUsingPUT(nbTeams);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamControllerApi#createTeamsUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **nbTeams** | **Integer**| nbTeams |

### Return type

[**List&lt;TeamDTO&gt;**](TeamDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllTeamMembersOfTeamByIdUsingGET"></a>
# **getAllTeamMembersOfTeamByIdUsingGET**
> List&lt;TeamMemberDTO&gt; getAllTeamMembersOfTeamByIdUsingGET(idTeam)

getAllTeamMembersOfTeamById

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamControllerApi;


TeamControllerApi apiInstance = new TeamControllerApi();
Integer idTeam = 56; // Integer | idTeam
try {
    List<TeamMemberDTO> result = apiInstance.getAllTeamMembersOfTeamByIdUsingGET(idTeam);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamControllerApi#getAllTeamMembersOfTeamByIdUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idTeam** | **Integer**| idTeam |

### Return type

[**List&lt;TeamMemberDTO&gt;**](TeamMemberDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getAllTeamsUsingGET"></a>
# **getAllTeamsUsingGET**
> List&lt;TeamDTO&gt; getAllTeamsUsingGET()

getAllTeams

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamControllerApi;


TeamControllerApi apiInstance = new TeamControllerApi();
try {
    List<TeamDTO> result = apiInstance.getAllTeamsUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamControllerApi#getAllTeamsUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;TeamDTO&gt;**](TeamDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getTeamByIdUsingGET"></a>
# **getTeamByIdUsingGET**
> TeamDTO getTeamByIdUsingGET(idTeam)

getTeamById

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamControllerApi;


TeamControllerApi apiInstance = new TeamControllerApi();
Integer idTeam = 56; // Integer | idTeam
try {
    TeamDTO result = apiInstance.getTeamByIdUsingGET(idTeam);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamControllerApi#getTeamByIdUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idTeam** | **Integer**| idTeam |

### Return type

[**TeamDTO**](TeamDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

