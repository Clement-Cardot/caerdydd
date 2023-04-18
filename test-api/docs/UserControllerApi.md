# UserControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUsingPUT**](UserControllerApi.md#addUsingPUT) | **PUT** /api/users | add
[**getUsingGET1**](UserControllerApi.md#getUsingGET1) | **GET** /api/users/{id} | get
[**listUsingGET1**](UserControllerApi.md#listUsingGET1) | **GET** /api/users | list
[**updateUsingPOST**](UserControllerApi.md#updateUsingPOST) | **POST** /api/users | update


<a name="addUsingPUT"></a>
# **addUsingPUT**
> String addUsingPUT(userDto)

add

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
UserDTO userDto = new UserDTO(); // UserDTO | userDto
try {
    String result = apiInstance.addUsingPUT(userDto);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#addUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userDto** | [**UserDTO**](UserDTO.md)| userDto |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUsingGET1"></a>
# **getUsingGET1**
> UserDTO getUsingGET1(id)

get

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
Integer id = 56; // Integer | id
try {
    UserDTO result = apiInstance.getUsingGET1(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#getUsingGET1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**| id |

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="listUsingGET1"></a>
# **listUsingGET1**
> List&lt;UserDTO&gt; listUsingGET1()

list

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
try {
    List<UserDTO> result = apiInstance.listUsingGET1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#listUsingGET1");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserDTO&gt;**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="updateUsingPOST"></a>
# **updateUsingPOST**
> String updateUsingPOST(userDto)

update

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
UserDTO userDto = new UserDTO(); // UserDTO | userDto
try {
    String result = apiInstance.updateUsingPOST(userDto);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#updateUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userDto** | [**UserDTO**](UserDTO.md)| userDto |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

