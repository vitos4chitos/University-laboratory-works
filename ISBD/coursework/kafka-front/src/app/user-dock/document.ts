export class Doc {
  id;
  name;
  date1;
  date2;
  bywho;
  lgot;
  podtver;
  pod;

  constructor(id: number, name: string, date1: Date, date2: Date, bywho: string, lgot: string, podtver: string, pod: string){
    this.id = id;
    this.name = name;
    this.bywho = bywho;
    this.date1 = date1;
    this.date2 = date2;
    this.lgot = lgot;
    this.podtver = podtver;
    this.pod = pod;
  }
};
