import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoTarefaRecorrenteExecucaoDetailComponent } from './anexo-tarefa-recorrente-execucao-detail.component';

describe('AnexoTarefaRecorrenteExecucao Management Detail Component', () => {
  let comp: AnexoTarefaRecorrenteExecucaoDetailComponent;
  let fixture: ComponentFixture<AnexoTarefaRecorrenteExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoTarefaRecorrenteExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoTarefaRecorrenteExecucaoDetailComponent,
              resolve: { anexoTarefaRecorrenteExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoTarefaRecorrenteExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoTarefaRecorrenteExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoTarefaRecorrenteExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoTarefaRecorrenteExecucaoDetailComponent);

      // THEN
      expect(instance.anexoTarefaRecorrenteExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
