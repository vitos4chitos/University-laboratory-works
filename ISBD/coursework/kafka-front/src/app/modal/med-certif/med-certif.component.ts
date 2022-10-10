import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.serv";
import {MainService} from "../../services/main.serv";
import {Router} from "@angular/router";

@Component({
  selector: 'app-med-certif',
  templateUrl: './med-certif.component.html',
  styleUrls: ['./med-certif.component.css']
})
export class MedCertifComponent implements OnInit {

  login: string;
  name: String;
  date1: Date;
  bywho: string;
  sale: number;
  prior: number;

  dock: {
    login: string;
    name: String;
    date1: Date;
    bywho: string;
    sale: number;
    prior: number;
  }

  constructor(private auth: AuthService, private mainServer: MainService, private router: Router){}

  ngOnInit(): void {
  }


  onSubmit(){
    // @ts-ignore
    this.login = localStorage.getItem("user");
    // @ts-ignore
    this.name = document.getElementById("name").value;
    // @ts-ignore
    this.date1 = document.getElementById("getdate").value;
    // @ts-ignore
    this.bywho = document.getElementById("gavebywho").value;
    // @ts-ignore
    this.sale = document.getElementById("sale").value;
    // @ts-ignore
    this.prior = document.getElementById("prior").value;
    this.dock = {
      login: this.login,
      name: this.name,
      date1: this.date1,
      bywho: this.bywho,
      sale: this.sale,
      prior: this.prior
    };
    console.log(this.dock.sale)
    this.auth.addPrior(this.dock);
  }

  nextStep(){

    this.router.navigateByUrl("mainU");
  }
}
