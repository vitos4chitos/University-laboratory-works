import { Component, OnInit } from '@angular/core';
import {UserDoc} from "../user-docs/user.doc";
import {Shop} from "./shop";
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";

@Component({
  selector: 'app-where-i-can-buy',
  templateUrl: './where-i-can-buy.component.html',
  styleUrls: ['./where-i-can-buy.component.css']
})
export class WhereICanBuyComponent implements OnInit {
  rows: Shop[] = [];
  url = "http://localhost:8080/bkp/getShops"
  url2 = "http://localhost:8080/bkp/buy"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("type")).subscribe(values => {
      console.log(this.rows.toString());
      this.rows = values;
    });
  }

  getExistingValues(username: string): Observable<Shop[]> {
    let params = new HttpParams().set("name", username);
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any) {
        console.log(point["issued_by_whom"]);
        return new Shop(point["id"], point["quantity"], point["cost"], point["time"]);
      }
    )));
  }
  onClick(id: number){
    console.log(id);
    let params = new HttpParams().set("login", <string>localStorage.getItem("user")).set("bookkeepingId", id).set("name", <string>localStorage.getItem("type"));
    this.http.get<any>(this.url2, {params: params}).subscribe(value => {
      console.log(value);
      if(value == true){
        localStorage.removeItem("type");
        this.router.navigateByUrl("shop")
      }
      else{
        alert("Не хватает средств")
      }
    },
        error => {
        console.log(error);
        alert("Ошибка сервера, попробуйте позже")
      });

  }
  back(){
    localStorage.removeItem("type")
    this.router.navigateByUrl("shop")
  }

  logOut(){
    this.auth.logout();
  }

}
