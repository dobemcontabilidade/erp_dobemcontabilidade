import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GrupoAcessoPadraoDetailComponent } from './grupo-acesso-padrao-detail.component';

describe('GrupoAcessoPadrao Management Detail Component', () => {
  let comp: GrupoAcessoPadraoDetailComponent;
  let fixture: ComponentFixture<GrupoAcessoPadraoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoAcessoPadraoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GrupoAcessoPadraoDetailComponent,
              resolve: { grupoAcessoPadrao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GrupoAcessoPadraoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoAcessoPadraoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoAcessoPadrao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GrupoAcessoPadraoDetailComponent);

      // THEN
      expect(instance.grupoAcessoPadrao()).toEqual(expect.objectContaining({ id: 123 }));
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
