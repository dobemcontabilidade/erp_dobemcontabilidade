import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepartamentoFuncionarioDetailComponent } from './departamento-funcionario-detail.component';

describe('DepartamentoFuncionario Management Detail Component', () => {
  let comp: DepartamentoFuncionarioDetailComponent;
  let fixture: ComponentFixture<DepartamentoFuncionarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartamentoFuncionarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DepartamentoFuncionarioDetailComponent,
              resolve: { departamentoFuncionario: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DepartamentoFuncionarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoFuncionarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load departamentoFuncionario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DepartamentoFuncionarioDetailComponent);

      // THEN
      expect(instance.departamentoFuncionario()).toEqual(expect.objectContaining({ id: 123 }));
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
