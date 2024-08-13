import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaEmpresaModeloDetailComponent } from './tarefa-empresa-modelo-detail.component';

describe('TarefaEmpresaModelo Management Detail Component', () => {
  let comp: TarefaEmpresaModeloDetailComponent;
  let fixture: ComponentFixture<TarefaEmpresaModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaEmpresaModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaEmpresaModeloDetailComponent,
              resolve: { tarefaEmpresaModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaEmpresaModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaEmpresaModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaEmpresaModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaEmpresaModeloDetailComponent);

      // THEN
      expect(instance.tarefaEmpresaModelo()).toEqual(expect.objectContaining({ id: 123 }));
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
