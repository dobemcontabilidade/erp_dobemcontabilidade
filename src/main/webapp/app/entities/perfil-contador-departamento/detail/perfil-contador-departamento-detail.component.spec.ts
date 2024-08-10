import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PerfilContadorDepartamentoDetailComponent } from './perfil-contador-departamento-detail.component';

describe('PerfilContadorDepartamento Management Detail Component', () => {
  let comp: PerfilContadorDepartamentoDetailComponent;
  let fixture: ComponentFixture<PerfilContadorDepartamentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilContadorDepartamentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PerfilContadorDepartamentoDetailComponent,
              resolve: { perfilContadorDepartamento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PerfilContadorDepartamentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilContadorDepartamentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load perfilContadorDepartamento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PerfilContadorDepartamentoDetailComponent);

      // THEN
      expect(instance.perfilContadorDepartamento()).toEqual(expect.objectContaining({ id: 123 }));
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
