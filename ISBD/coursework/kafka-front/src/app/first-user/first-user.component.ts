import { Component, OnInit } from '@angular/core';
import {FirstUser} from "./first.user";
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-first-user',
  templateUrl: './first-user.component.html',
  styleUrls: ['./first-user.component.css']
})
export class FirstUserComponent implements OnInit {
  rows: FirstUser;
  url = "http://localhost:8080/queue/firstUser"
  url2 = "http://localhost:8080/signatures/makeSign"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkTokenO()
    this.getExistingValues(<string>localStorage.getItem("user"));
  }

  getExistingValues(username: string){
    let params = new HttpParams().set("login", username);
    this.http.get<any>(this.url, {params: params}).subscribe(value => {
        this.rows = new FirstUser(value["id"], value["name"], value["surname"], value["dockname"]);
      },
      error => {
        console.log(error);
      })
  }
  onClick(id: number){
    let username = id;
    let password = localStorage.getItem("user");
    let sign = {
      userId: username,
      loginOff: password
    };
    console.log(sign)
    this.http.post(this.url2, sign).subscribe(
      (res: any) => {
        if (res.toString() == "true") {
          this.router.navigateByUrl("mainO");
        } else {
          this.router.navigateByUrl("mainO");
        }
      },
      error => {
        alert("Что-то не так с сервером, попробуйте позже")
      }
    );
    console.log(id);
  }
  back(){
    this.router.navigateByUrl("mainO")
  }

  logOut(){
    this.auth.logout();
  }

}
