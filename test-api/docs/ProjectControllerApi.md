# ProjectControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**updateProjectUsingPUT**](ProjectControllerApi.md#updateProjectUsingPUT) | **PUT** /teams/{teamId}/projects/{projectId} | updateProject


<a name="updateProjectUsingPUT"></a>
# **updateProjectUsingPUT**
> ProjectDTO updateProjectUsingPUT(projectDTO, projectId, teamId, userId)

updateProject

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectControllerApi;


ProjectControllerApi apiInstance = new ProjectControllerApi();
ProjectDTO projectDTO = new ProjectDTO(); // ProjectDTO | projectDTO
Integer projectId = 56; // Integer | projectId
Integer teamId = 56; // Integer | teamId
Integer userId = 56; // Integer | userId
try {
    ProjectDTO result = apiInstance.updateProjectUsingPUT(projectDTO, projectId, teamId, userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectControllerApi#updateProjectUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectDTO** | [**ProjectDTO**](ProjectDTO.md)| projectDTO |
 **projectId** | **Integer**| projectId |
 **teamId** | **Integer**| teamId |
 **userId** | **Integer**| userId |

### Return type

[**ProjectDTO**](ProjectDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

