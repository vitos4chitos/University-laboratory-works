import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {UserDoc} from "./user.doc";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-user-docs',
  templateUrl: './user-docs.component.html',
  styleUrls: ['./user-docs.component.css']
})
export class UserDocsComponent implements OnInit {
  rows: UserDoc[] = [];
  url = "http://localhost:8080/document/getAll"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("user")).subscribe(values => {
      console.log(this.rows.toString());
      this.rows = values;
    });
  }

  getExistingValues(username: string): Observable<UserDoc[]> {
    let params = new HttpParams().set("login", username);
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any) {
      console.log(point["issued_by_whom"]);
      return new UserDoc(point["id"], point["name"], point["issue"], point["validity"], point["bywhom"]);
    }
    )));
  }
  onClick(id: number){
    localStorage.setItem('documentid', String(id));
    this.router.navigateByUrl("document");
    console.log(id);
  }
  back(){
    this.router.navigateByUrl("mainU")
  }

  logOut(){
    this.auth.logout();
  }

}
