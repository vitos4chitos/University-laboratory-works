import { Component, OnInit } from '@angular/core';
import {UserDoc} from "../user-docs/user.doc";
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {UserQueue} from "./user.queue";

@Component({
  selector: 'app-user-queue',
  templateUrl: './user-queue.component.html',
  styleUrls: ['./user-queue.component.css']
})
export class UserQueueComponent implements OnInit {

  rows: UserQueue[] = [];
  url = "http://localhost:8080/queue/getUserQueue"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("user")).subscribe(values => {
      console.log(this.rows.toString());
      this.rows = values;
    });
  }

  getExistingValues(username: string): Observable<UserQueue[]> {
    let params = new HttpParams().set("login", username);
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any) {
      console.log(point["name"]);
      return new UserQueue(point["id"], point["name"], point["place"], point["priority"], point["time"]);
    })));
  }
  onClick(id: number){
    console.log(id);
  }

  back(){
    this.router.navigateByUrl("mainU")
  }

  logOut(){
    this.auth.logout();
  }

}
