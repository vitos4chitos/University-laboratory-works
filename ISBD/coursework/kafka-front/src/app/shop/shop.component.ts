import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {UserPodpicy} from "./user.podpicy";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  rows: UserPodpicy[] = [];
  url = "http://localhost:8080/bkp/getAll"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("user")).subscribe(values => {
      console.log(this.rows.toString());
      this.rows = values;
    });
  }

  getExistingValues(username: string): Observable<UserPodpicy[]> {
    let params = new HttpParams().set("login", username);
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any) {
      console.log(point["issued_by_whom"]);
      return new UserPodpicy(point["id"], point["name"]);
    })));
  }
  onClick(id: String){
    console.log(id);
    localStorage.setItem("type", String(id))
    this.router.navigateByUrl("shops");
  }
  back(){
    this.router.navigateByUrl("mainU")
  }

  logOut(){
    this.auth.logout();
  }
}
