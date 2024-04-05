import { Component, HostListener } from '@angular/core';
import { ViewportScroller } from "@angular/common"

@Component({
  selector: 'core-widgets-tap-to-top',
  template: `
    <button *ngIf="show" (click)="tapToTop()" class="btn btn-outline-info btn-sm rounded-circle">
      <i class="bi bi-chevron-up"></i>
    </button>
  `,
  styles: `
    button {
      position: fixed;
      bottom: 5px;
      right: 5px;
      font-size: 20px;
      text-align: center;
    }
  `
})
export class TapToTopComponent {

  public show: boolean = false;

  constructor(private viewScroller: ViewportScroller) { }

  // @HostListener Decorator
  @HostListener("window:scroll", [])
  onWindowScroll() {
    let position = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;

    if (position > 100) {
      this.show = true;
    }
    else {
      this.show = false;
    }
  }

  tapToTop() {
    this.viewScroller.scrollToPosition([0, 0]);
  }

}
