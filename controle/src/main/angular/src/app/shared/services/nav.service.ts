import { Injectable, OnDestroy } from '@angular/core';
import { Subject, BehaviorSubject, fromEvent } from 'rxjs';
import { takeUntil, debounceTime } from 'rxjs/operators';
import { Router } from '@angular/router';

// Menu
export interface Menu {
	headTitle1?: string,
	headTitle2?: string,
	path?: string;
	title?: string;
	icon?: string;
	type?: string;
	badgeType?: string;
	badgeValue?: string;
	active?: boolean;
	bookmark?: boolean;
	children?: Menu[];
}

@Injectable({
	providedIn: 'root'
})

export class NavService implements OnDestroy {

	private unsubscriber: Subject<any> = new Subject();
	public  screenWidth: BehaviorSubject<number> = new BehaviorSubject(window.innerWidth);

	// Search Box
	public search: boolean = false;

	// Language
	public language: boolean = false;
	
	// Mega Menu
	public megaMenu: boolean = false;
	public levelMenu: boolean = false;
	public megaMenuColapse: boolean = window.innerWidth < 1199 ? true : false;

	// For Horizontal Layout Mobile
	public horizontal: boolean = window.innerWidth < 991 ? false : true;

	// Collapse Sidebar
	public collapseSidebar: boolean = window.innerWidth < 991 ? true : false;

	// Full screen
	public fullScreen: boolean = false;

	constructor(private router: Router) {
		this.setScreenWidth(window.innerWidth);
		fromEvent(window, 'resize').pipe(
			debounceTime(1000),
			takeUntil(this.unsubscriber)
		).subscribe((evt: any) => {
			this.setScreenWidth(evt.target.innerWidth);
			if (evt.target.innerWidth < 991) {
				this.collapseSidebar = true;
				this.megaMenu = false;
				this.levelMenu = false;
			}
			if(evt.target.innerWidth < 1199) {
				this.megaMenuColapse = true;
			}
		});
		if(window.innerWidth < 991) { // Detect Route change sidebar close
			this.router.events.subscribe(event => { 
				this.collapseSidebar = true;
				this.megaMenu = false;
				this.levelMenu = false;
			});
		}
	}

	ngOnDestroy() {
		this.unsubscriber.complete();
	}

	private setScreenWidth(width: number): void {
		this.screenWidth.next(width);
	}

	MENUITEMS: Menu[] = [
		{
			headTitle1: 'Menu de Opções', headTitle2: 'Selecione uma das opções abaixo.',
		},
		{ path: '/home', title: 'Início', icon: 'home', type: 'link', active: true },
		{ path: '/sistema/pesquisar', title: 'Sistemas', icon: 'codesandbox', type: 'link', active: false },
		{ path: '/usuario/pesquisar', title: 'Usuários', icon: 'user', type: 'link', active: false }
	];

	MEGAMENUITEMS: Menu[] = [
		{
			title: 'Início', type: 'sub', active: true, children: [
				{ path: '/home', title: 'Página Inicial', type: 'link' }
			]
		},
		{
			title: 'Sistemas', type: 'sub', active: true, children: [
				{ path: '/sistema/pesquisar', title: 'Pesquisar sistemas', type: 'link' },
				{ path: '/sistema/cadastro', title: 'Novo sistema', type: 'link' }
			]
		},
		{
			title: 'Usuários', type: 'sub', active: true, children: [
				{ path: '/usuario/pesquisar', title: 'Pesquisar usuários', type: 'link' },
				{ path: '/usuario/cadastro', title: 'Novo usuário', type: 'link' }
			]
		}

	];

	LEVELMENUITEMS: Menu[] = [
	];

	// Array
	items = new BehaviorSubject<Menu[]>(this.MENUITEMS);
	megaItems = new BehaviorSubject<Menu[]>(this.MEGAMENUITEMS);
	levelmenuitems = new BehaviorSubject<Menu[]>(this.LEVELMENUITEMS);

}
