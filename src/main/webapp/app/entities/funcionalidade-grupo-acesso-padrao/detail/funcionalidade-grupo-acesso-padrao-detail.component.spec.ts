import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuncionalidadeGrupoAcessoPadraoDetailComponent } from './funcionalidade-grupo-acesso-padrao-detail.component';

describe('FuncionalidadeGrupoAcessoPadrao Management Detail Component', () => {
  let comp: FuncionalidadeGrupoAcessoPadraoDetailComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoPadraoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoPadraoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FuncionalidadeGrupoAcessoPadraoDetailComponent,
              resolve: { funcionalidadeGrupoAcessoPadrao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuncionalidadeGrupoAcessoPadraoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoPadraoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load funcionalidadeGrupoAcessoPadrao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuncionalidadeGrupoAcessoPadraoDetailComponent);

      // THEN
      expect(instance.funcionalidadeGrupoAcessoPadrao()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
