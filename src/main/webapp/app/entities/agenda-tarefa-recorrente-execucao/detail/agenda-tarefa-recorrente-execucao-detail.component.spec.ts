import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgendaTarefaRecorrenteExecucaoDetailComponent } from './agenda-tarefa-recorrente-execucao-detail.component';

describe('AgendaTarefaRecorrenteExecucao Management Detail Component', () => {
  let comp: AgendaTarefaRecorrenteExecucaoDetailComponent;
  let fixture: ComponentFixture<AgendaTarefaRecorrenteExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgendaTarefaRecorrenteExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AgendaTarefaRecorrenteExecucaoDetailComponent,
              resolve: { agendaTarefaRecorrenteExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgendaTarefaRecorrenteExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgendaTarefaRecorrenteExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agendaTarefaRecorrenteExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgendaTarefaRecorrenteExecucaoDetailComponent);

      // THEN
      expect(instance.agendaTarefaRecorrenteExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
