import {Component, OnInit} from '@angular/core';
import {Customer} from "./customer";
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";


@Component({
  selector: 'app-main-official-page',
  templateUrl: './main-official-page.component.html',
  styleUrls: ['./main-official-page.component.css']
})


export class MainOfficialPageComponent implements OnInit {

  customers: Customer[] = [];

  url = "http://localhost:8080/queue/official"
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.auth.checkTokenO()
    this.getExistingValues(<string>localStorage.getItem("user")).subscribe(values => {
      console.log(this.customers.toString());
      this.customers = values;
    });
  }

  getExistingValues(username: string): Observable<Customer[]> {
    let params = new HttpParams().set("login", username);
    return this.http.get<any>(this.url, {params: params}).pipe(map(points => points.map(function (point: any) {
        console.log(point["id"]);
        return new Customer(point["id"], point["name"], point["place"], point["prior"]);
      }
    )));
  }

  // customSort(event: SortEvent) {
  //   event.data.sort((data1, data2) => {
  //     let value1 = data1[event.field];
  //     let value2 = data2[event.field];
  //     let result = null;
  //
  //     if (value1 == null && value2 != null)
  //       result = -1;
  //     else if (value1 != null && value2 == null)
  //       result = 1;
  //     else if (value1 == null && value2 == null)
  //       result = 0;
  //     else if (typeof value1 === 'string' && typeof value2 === 'string')
  //       result = value1.localeCompare(value2);
  //     else
  //       result = (value1 < value2) ? -1 : (value1 > value2) ? 1 : 0;
  //
  //     return (event.order * result);
  //   });
  // }

  //todo
  acceptCustomer() {
    this.router.navigateByUrl("firstUser")
  }
  logOut() {
    this.auth.logout();
  }
}
