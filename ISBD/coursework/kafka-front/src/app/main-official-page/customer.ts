export class Customer {
  id;
  name;
  numberInQueue;
  priority

  constructor(id: number, name: string, numberInQueue: number, priority: number){
    this.id = id;
    this.name = name;
    this.numberInQueue = numberInQueue;
    this.priority = priority;
  }
};
