import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GrupoAcessoEmpresaUsuarioContadorDetailComponent } from './grupo-acesso-empresa-usuario-contador-detail.component';

describe('GrupoAcessoEmpresaUsuarioContador Management Detail Component', () => {
  let comp: GrupoAcessoEmpresaUsuarioContadorDetailComponent;
  let fixture: ComponentFixture<GrupoAcessoEmpresaUsuarioContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoAcessoEmpresaUsuarioContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GrupoAcessoEmpresaUsuarioContadorDetailComponent,
              resolve: { grupoAcessoEmpresaUsuarioContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GrupoAcessoEmpresaUsuarioContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoAcessoEmpresaUsuarioContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoAcessoEmpresaUsuarioContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GrupoAcessoEmpresaUsuarioContadorDetailComponent);

      // THEN
      expect(instance.grupoAcessoEmpresaUsuarioContador()).toEqual(expect.objectContaining({ id: 123 }));
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
