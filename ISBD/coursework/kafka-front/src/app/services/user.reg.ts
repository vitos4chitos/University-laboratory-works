import {Time} from "@angular/common";

export interface UserToReg{
  username: string;
  password: string;
  name: string;
  surname: string;
  money: number;
  role: string,
  active: boolean,
  date: Date
}
