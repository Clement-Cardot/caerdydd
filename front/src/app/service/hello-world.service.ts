import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageModel } from '../model/message';

@Injectable({
    providedIn: 'root'
})
export class HelloWorldService {
    constructor(private http: HttpClient) {
    }
    executeHelloWorldService() {
        return this.http.get<MessageModel>('http://localhost:4200/api/helloworld?name=clement');
    }
}