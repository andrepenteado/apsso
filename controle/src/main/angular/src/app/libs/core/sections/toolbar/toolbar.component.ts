import { Component } from '@angular/core';
import { MENU } from "../../../../etc/menu";
import { Layout } from "../../../../etc/layout";

@Component({
  selector: 'core-section-toolbar',
  templateUrl: './toolbar.component.html',
  styles: ``
})
export class ToolbarComponent {

  tema() {
    const body=document.body as HTMLElement;
    body.setAttribute('data-bs-theme', 'dark');
  }

  getMenu() {
    return MENU
  }

  getLayout() {
    return Layout;
  }

}
