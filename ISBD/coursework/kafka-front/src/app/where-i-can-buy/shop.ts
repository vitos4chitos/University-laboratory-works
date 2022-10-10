import {Time} from "@angular/common";

export class Shop {
  id;
  quantity;
  cost;
  time;

  constructor(id: number, quantity: number, cost: number, time: string){
    this.id = id;
    this.quantity = quantity;
    this.cost = cost;
    this.time = time;
  }
};
