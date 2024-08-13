import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaRecorrenteEmpresaModeloDetailComponent } from './tarefa-recorrente-empresa-modelo-detail.component';

describe('TarefaRecorrenteEmpresaModelo Management Detail Component', () => {
  let comp: TarefaRecorrenteEmpresaModeloDetailComponent;
  let fixture: ComponentFixture<TarefaRecorrenteEmpresaModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaRecorrenteEmpresaModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaRecorrenteEmpresaModeloDetailComponent,
              resolve: { tarefaRecorrenteEmpresaModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaRecorrenteEmpresaModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaRecorrenteEmpresaModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaRecorrenteEmpresaModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaRecorrenteEmpresaModeloDetailComponent);

      // THEN
      expect(instance.tarefaRecorrenteEmpresaModelo()).toEqual(expect.objectContaining({ id: 123 }));
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
