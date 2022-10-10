export class UserQueue {
  id;
  ofName;
  time;
  place;
  priority;

  constructor(id: number, ofName: String, place: number, priority: number, time: string){
    this.id = id;
    this.ofName = ofName;
    this.time = time;
    this.place = place;
    this.priority = priority;
  }
};
