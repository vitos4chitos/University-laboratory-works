import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Doc} from "./document";

@Component({
  selector: 'app-user-dock',
  templateUrl: './user-dock.component.html',
  styleUrls: ['./user-dock.component.css']
})
export class UserDockComponent implements OnInit {
  rows: Doc[] = [];
  url = "http://localhost:8080/document/getDock"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit(){
    this.auth.checkToken()
    this.getExistingValues(<string>localStorage.getItem("documentid"));
  }

  getExistingValues(username: string){
    let params = new HttpParams().set("id", username);
    this.http.get<any>(this.url, {params: params}).subscribe(value => {
      this.rows[0] = new Doc(value["id"], value["name"], value["issue"], value["validity"], value["byWho"], value["lgot"], value["podtver"], value["pod"]);
    },
    error => {
      console.log(error);
    })
  }
  onClick(id: number){
    console.log(id);
  }
  back(){
    localStorage.removeItem("documentid");
    this.router.navigateByUrl("documents")
  }

  logOut(){
    this.auth.logout();
  }

}
