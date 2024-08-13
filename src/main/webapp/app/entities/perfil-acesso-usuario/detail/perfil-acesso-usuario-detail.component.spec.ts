import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PerfilAcessoUsuarioDetailComponent } from './perfil-acesso-usuario-detail.component';

describe('PerfilAcessoUsuario Management Detail Component', () => {
  let comp: PerfilAcessoUsuarioDetailComponent;
  let fixture: ComponentFixture<PerfilAcessoUsuarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilAcessoUsuarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PerfilAcessoUsuarioDetailComponent,
              resolve: { perfilAcessoUsuario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PerfilAcessoUsuarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilAcessoUsuarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load perfilAcessoUsuario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PerfilAcessoUsuarioDetailComponent);

      // THEN
      expect(instance.perfilAcessoUsuario()).toEqual(expect.objectContaining({ id: 123 }));
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
