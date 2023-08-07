import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'ap-floating-button',
  template: `
    <button class="btn btn-primary float-button" [disabled]="disabled">
      <ng-content></ng-content>
    </button>
  `,
  styles: [`
    .float-button {
      position: fixed;
      height: 70px;
      bottom: 20px;
      right: 20px;
      padding-left: 25px;
      line-height: 0px;
      border-radius: 50px;
      border: 0px;
      text-align: center;
      box-shadow: 5px 5px 10px #555;
      z-index: 1000;
      animation: bot-to-top 2s ease-out;
      outline: none;
    }
    @media (max-width: 991.98px) {
      .float-button {
        width: 70px;
      }
    }
    @media (min-width: 992px) {
      .float-button {
        width: 70px;
      }
    }
    .float-button + ul {
      visibility: hidden;
    }
    .float-button:hover + ul {
      visibility: visible;
      animation: scale-in 0.5s;
    }
    .float-button span {
      animation: rotate-in 0.5s;
    }
    .float-button:hover > span {
      animation: rotate-out 0.5s;
    }
    .float-menu {
      position: fixed;
      right: 20px;
      padding-bottom: 20px;
      bottom: 50px;
      z-index: 100;
    }
    .float-menu:hover {
      visibility: visible !important;
      opacity: 1 !important;
    }
    .float-menu li {
      list-style: none;
      margin-bottom: 10px;
    }
    .float-menu li a {
      background-color: #007bff;
      color: #FFF;
      border-radius: 50px;
      text-align: center;
      line-height: 60px;
      box-shadow: 2px 2px 3px #999;
      width: 60px;
      height: 60px;
      display: block;
    }
    @keyframes bot-to-top {
      0% {
        bottom: -40px
      }
      50% {
        bottom: 40px
      }
    }
    @keyframes scale-in {
      from {
        transform: scale(0);
        opacity: 0;
      }
      to {
        transform: scale(1);
        opacity: 1;
      }
    }
    @keyframes rotate-in {
      from {
        transform: rotate(0deg);
      }
      to {
        transform: rotate(360deg);
      }
    }
    @keyframes rotate-out {
      from {
        transform: rotate(360deg);
      }
      to {
        transform: rotate(0deg);
      }
    }
  `]
})
export class FloatingButtonComponent implements OnInit {

  @Input()
  disabled: string;

  constructor() { }

  ngOnInit(): void {
  }

}
