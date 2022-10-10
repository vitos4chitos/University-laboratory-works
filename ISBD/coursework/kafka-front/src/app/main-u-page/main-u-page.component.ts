import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {AuthUserComponent} from "../auth-user/auth-user.component";
import {HttpClient, HttpParams} from "@angular/common/http";
import {UserInfo} from "./user.info";

@Component({
  selector: 'app-main-u-page',
  templateUrl: './main-u-page.component.html',
  styleUrls: ['./main-u-page.component.css']
})
export class MainUPageComponent implements OnInit {
  url2 = "http://localhost:8080/user/instance"
  user: UserInfo;
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  login: string | null = localStorage.getItem("user");

  ngOnInit(): void {
    this.auth.checkToken()
    this.getInstance()
  }
  logOut(){
    this.auth.logout();
  }
  toD(){
    this.router.navigateByUrl("documents")
  }

  toQ(){
    this.router.navigateByUrl("queues")
  }

  toS(){
    this.router.navigateByUrl("shop")
  }
  toL(){
    this.router.navigateByUrl("nextLvl")
  }

  getInstance(){
    let params = new HttpParams().set("login", <string>localStorage.getItem("user"));
    this.http.get<any>(this.url2, {params: params}).subscribe(value => {
        console.log(value);
        this.user = new UserInfo(value["id"], value["money"])
      },
      error => {
        console.log(error);
        this.router.navigateByUrl("")
      });

  }
}
