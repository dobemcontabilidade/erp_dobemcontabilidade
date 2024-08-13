import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuncionalidadeGrupoAcessoEmpresaDetailComponent } from './funcionalidade-grupo-acesso-empresa-detail.component';

describe('FuncionalidadeGrupoAcessoEmpresa Management Detail Component', () => {
  let comp: FuncionalidadeGrupoAcessoEmpresaDetailComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FuncionalidadeGrupoAcessoEmpresaDetailComponent,
              resolve: { funcionalidadeGrupoAcessoEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuncionalidadeGrupoAcessoEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load funcionalidadeGrupoAcessoEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuncionalidadeGrupoAcessoEmpresaDetailComponent);

      // THEN
      expect(instance.funcionalidadeGrupoAcessoEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
