# AuthControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**loginUsingPOST**](AuthControllerApi.md#loginUsingPOST) | **POST** /api/auth/login | login
[**logoutUserUsingPOST**](AuthControllerApi.md#logoutUserUsingPOST) | **POST** /api/auth/logout | logoutUser


<a name="loginUsingPOST"></a>
# **loginUsingPOST**
> UserDTO loginUsingPOST(requestUser)

login

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuthControllerApi;


AuthControllerApi apiInstance = new AuthControllerApi();
UserDTO requestUser = new UserDTO(); // UserDTO | requestUser
try {
    UserDTO result = apiInstance.loginUsingPOST(requestUser);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthControllerApi#loginUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **requestUser** | [**UserDTO**](UserDTO.md)| requestUser |

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="logoutUserUsingPOST"></a>
# **logoutUserUsingPOST**
> Object logoutUserUsingPOST()

logoutUser

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuthControllerApi;


AuthControllerApi apiInstance = new AuthControllerApi();
try {
    Object result = apiInstance.logoutUserUsingPOST();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthControllerApi#logoutUserUsingPOST");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

