import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GrupoAcessoUsuarioContadorDetailComponent } from './grupo-acesso-usuario-contador-detail.component';

describe('GrupoAcessoUsuarioContador Management Detail Component', () => {
  let comp: GrupoAcessoUsuarioContadorDetailComponent;
  let fixture: ComponentFixture<GrupoAcessoUsuarioContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoAcessoUsuarioContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GrupoAcessoUsuarioContadorDetailComponent,
              resolve: { grupoAcessoUsuarioContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GrupoAcessoUsuarioContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoAcessoUsuarioContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoAcessoUsuarioContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GrupoAcessoUsuarioContadorDetailComponent);

      // THEN
      expect(instance.grupoAcessoUsuarioContador()).toEqual(expect.objectContaining({ id: 123 }));
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
