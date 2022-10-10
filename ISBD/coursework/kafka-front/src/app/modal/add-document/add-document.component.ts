import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.serv";
import {MainService} from "../../services/main.serv";
import {Router} from "@angular/router";
import {routes} from "../../routing/routes";

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css']
})
export class AddDocumentComponent implements OnInit {

  names: string[];
  selectedName: string = '';

  constructor(private auth: AuthService, private mainServer: MainService, private router: Router) {
    this.names = [
      'Пасспорт',
      'Свидетельство о рождении',
      'Права на вождение автомобиля',
      'Справка с места работы'
    ];
  }

  login: string;
  name: String;
  date1: Date;
  date2: Date;
  bywho: string;

  dock: {
    login: string;
    name: String;
    date1: Date;
    date2: Date;
    bywho: string;
  }

  ngOnInit(): void {
  }

  onSubmit() {
    // @ts-ignore
    this.login = localStorage.getItem("user");
    // @ts-ignore
    this.name = document.getElementById("name").value;
    // @ts-ignore
    this.date1 = document.getElementById("getdate").value;
    // @ts-ignore
    this.date2 = document.getElementById("getperiod").value;
    // @ts-ignore
    this.bywho = document.getElementById("gavebywho").value;
    this.dock = {
      login: this.login,
      name: this.selectedName,
      date1: this.date1,
      date2: this.date2,
      bywho: this.bywho,
    };
    console.log(this.selectedName);
    this.auth.addDock(this.dock)
  }

  nextStep(){
    this.router.navigateByUrl("registprimes");
  }

}
