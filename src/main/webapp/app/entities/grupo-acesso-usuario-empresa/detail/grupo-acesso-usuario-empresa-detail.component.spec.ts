import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GrupoAcessoUsuarioEmpresaDetailComponent } from './grupo-acesso-usuario-empresa-detail.component';

describe('GrupoAcessoUsuarioEmpresa Management Detail Component', () => {
  let comp: GrupoAcessoUsuarioEmpresaDetailComponent;
  let fixture: ComponentFixture<GrupoAcessoUsuarioEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoAcessoUsuarioEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GrupoAcessoUsuarioEmpresaDetailComponent,
              resolve: { grupoAcessoUsuarioEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GrupoAcessoUsuarioEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoAcessoUsuarioEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoAcessoUsuarioEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GrupoAcessoUsuarioEmpresaDetailComponent);

      // THEN
      expect(instance.grupoAcessoUsuarioEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
