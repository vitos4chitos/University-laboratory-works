import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {UserNext} from "./user.next";

@Component({
  selector: 'app-to-the-next-lvl',
  templateUrl: './to-the-next-lvl.component.html',
  styleUrls: ['./to-the-next-lvl.component.css']
})
export class ToTheNextLvlComponent implements OnInit {

  rows: UserNext[] = [];
  url = "http://localhost:8080/document/getAllReference"
  url2 = "http://localhost:8080/document/addForReview"
  url_lvl = "http://localhost:8080/instance/nextLevel"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    console.log("Привет")
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("user")).subscribe(values => {
      console.log(this.rows.toString());
      this.rows = values;
    });
  }

  getExistingValues(username: string): Observable<UserNext[]> {
    let params = new HttpParams().set("login", username);
    console.log("Я отправил запрос летс гоу");
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any){
        return new UserNext(point["id"], point["name"], point["sign"], point["check"]);
      }
    )));
  }
  onClick(id: number){
    console.log(id);
    let params = new HttpParams().set("parameterId", id);
    console.log("Я отправил запрос летс гоу");
    this.http.get<any>(this.url2, {params: params}).subscribe(value => {
        alert("Успешно добавлен");
      },
      error => {
      alert("Уже добавлен, или не хватает подписей, или всё занято")
      }
    );
  }

  back(){
    this.router.navigateByUrl("mainU")
  }

  logOut(){
    this.auth.logout();
  }

  nextLvl(){
    let params = new HttpParams().set("login", <string>localStorage.getItem("user"));
    console.log("Я отправил запрос летс гоу");
    this.http.get<any>(this.url_lvl, {params: params}).subscribe(value => {
        if(value["token"] === "true"){
          this.router.navigateByUrl("mainU")
        }
        else{
          alert("Пока не можем этого сделать")
        }
      },
      error => {
        alert("Попробуйте позже")
      }
    );
    console.log("next")
  }

}
