import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {User} from "../services/interfaces";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MainService} from "../services/main.serv";
import {AuthService} from "../services/auth.serv";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth-user',
  templateUrl: './auth-user.component.html',
  styleUrls: ['./auth-user.component.css']
})

export class AuthUserComponent implements OnInit {
  items: MenuItem[] = [];

  activeItem: MenuItem = {};

  username: string;
  password: string;
  log: string;

  user:{
    login: string;
    password: string;
  }
  constructor(private auth: AuthService, private mainServer: MainService, private router: Router){}
  ngOnInit() {
    this.items = [
      {label: 'Пользователь'},
      {label: 'Чиновник'}
    ];

    this.activeItem = this.items[0];
  }

  onSubmit(data: string) {
    // @ts-ignore
    this.log = document.getElementById("tab");
    console.log(this.activeItem);
    if(data === 'LogIn'){
      // @ts-ignore
      this.username = document.getElementById("email1").value;
      // @ts-ignore
      this.password = document.getElementById("password1").value;
      this.user = {
        login: this.username,
        password: this.password
      };
      if(this.activeItem == this.items[0]){
        this.mainServer.setCurrentUser(this.user.login);
        localStorage.setItem("user", this.mainServer.currentUser);
        localStorage.setItem("role", "user");
        console.log(this.user.login);
        this.auth.login(this.user);
      }
      else{
        this.mainServer.setCurrentUser(this.user.login);
        localStorage.setItem("user", this.mainServer.currentUser);
        localStorage.setItem("role", "admin");
        console.log(this.user.login);
        this.auth.login(this.user);
      }
    }
    else{
      this.router.navigateByUrl("registU");
      localStorage.setItem("role", "user");
    }
  }
  change(){
    if(this.activeItem == this.items[0]){
      this.activeItem = this.items[1];
    }
    else{
      this.activeItem = this.items[0];
    }
  }
}


