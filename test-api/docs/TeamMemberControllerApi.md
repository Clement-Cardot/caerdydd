# TeamMemberControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUsingGET**](TeamMemberControllerApi.md#getUsingGET) | **GET** /api/teamMembers/{id} | get
[**listUsingGET**](TeamMemberControllerApi.md#listUsingGET) | **GET** /api/teamMembers | list
[**setBonusPenaltyUsingPOST**](TeamMemberControllerApi.md#setBonusPenaltyUsingPOST) | **POST** /api/teamMembers/bonus/{id}/{bonus} | setBonusPenalty


<a name="getUsingGET"></a>
# **getUsingGET**
> TeamMemberDTO getUsingGET(id)

get

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamMemberControllerApi;


TeamMemberControllerApi apiInstance = new TeamMemberControllerApi();
Integer id = 56; // Integer | id
try {
    TeamMemberDTO result = apiInstance.getUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMemberControllerApi#getUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**| id |

### Return type

[**TeamMemberDTO**](TeamMemberDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="listUsingGET"></a>
# **listUsingGET**
> List&lt;TeamMemberDTO&gt; listUsingGET()

list

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamMemberControllerApi;


TeamMemberControllerApi apiInstance = new TeamMemberControllerApi();
try {
    List<TeamMemberDTO> result = apiInstance.listUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMemberControllerApi#listUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;TeamMemberDTO&gt;**](TeamMemberDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="setBonusPenaltyUsingPOST"></a>
# **setBonusPenaltyUsingPOST**
> TeamMemberDTO setBonusPenaltyUsingPOST(bonus, id)

setBonusPenalty

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TeamMemberControllerApi;


TeamMemberControllerApi apiInstance = new TeamMemberControllerApi();
Integer bonus = 56; // Integer | bonus
Integer id = 56; // Integer | id
try {
    TeamMemberDTO result = apiInstance.setBonusPenaltyUsingPOST(bonus, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamMemberControllerApi#setBonusPenaltyUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bonus** | **Integer**| bonus |
 **id** | **Integer**| id |

### Return type

[**TeamMemberDTO**](TeamMemberDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

