import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TemaService {

  public static readonly TEMA_COOKIE = 'APCODE_TEMA_COOKIE';

  constructor() {
    let tema = sessionStorage.getItem(TemaService.TEMA_COOKIE);
    if (tema == null || tema == "")
      tema = "light";
    this.setDark(tema == "dark");
  }

  setDark(dark: boolean) {
    let tema = dark ? "dark" : "light";
    sessionStorage.setItem(TemaService.TEMA_COOKIE, tema);
    const body= document.body as HTMLElement;
    body.setAttribute("data-bs-theme", tema);
  }

  isDark(): boolean {
    let dark = sessionStorage.getItem(TemaService.TEMA_COOKIE);
    return dark == "dark";
  }

}
