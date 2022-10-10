import { Component, OnInit } from '@angular/core';
import {Time} from "@angular/common";
import {AuthService} from "../services/auth.serv";
import {MainService} from "../services/main.serv";
import {Router} from "@angular/router";

@Component({
  selector: 'app-regist-user',
  templateUrl: './regist-user.component.html',
  styleUrls: ['./regist-user.component.css']
})
export class RegistUserComponent implements OnInit {

  constructor(private auth: AuthService, private mainServer: MainService, private router: Router) { }
  username: string;
  password: string;
  name: string;
  surname: string;
  money: number;
  date: Date = new Date();
  time: Time;

  user:{
    username: string;
    password: string;
    name: string;
    surname: string;
    money: number;
    role: string;
    active: boolean;
    date: Date;
  }

  ngOnInit(): void {
  }

  onSubmit() {
      // @ts-ignore
      this.username = document.getElementById("login").value;
      // @ts-ignore
      this.password = document.getElementById("password").value;
    // @ts-ignore
    this.name = document.getElementById("name").value;
    // @ts-ignore
    this.surname = document.getElementById("surname").value;
    // @ts-ignore
    this.money = document.getElementById("money").value;
    // @ts-ignore
    this.date = document.getElementById("getdate").value;
    // @ts-ignore
    this.time = document.getElementById("gettime").value;
      this.user = {
        username: this.username,
        password: this.password,
        name: this.name,
        surname: this.surname,
        money: this.money,
        role: 'user',
        active: true,
        date: this.date,
      };
      this.mainServer.setCurrentUser(this.user.username);
      localStorage.setItem("user", this.mainServer.currentUser);
      console.log(this.user.date);
      this.auth.reg(this.user)
    }
  }
