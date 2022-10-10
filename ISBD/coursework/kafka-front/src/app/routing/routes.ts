import {Routes} from '@angular/router';
import {StartPageComponent} from "../start-page/start-page.component";
import {MainUPageComponent} from "../main-u-page/main-u-page.component";
import {AuthGuard} from "./guard";

export const routes: Routes = [
  {path: '', component: StartPageComponent},
  {path: 'main', component: MainUPageComponent},
  {path: '**', redirectTo: '', canActivate: [AuthGuard]}
];
