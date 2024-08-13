import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaRecorrenteExecucaoDetailComponent } from './tarefa-recorrente-execucao-detail.component';

describe('TarefaRecorrenteExecucao Management Detail Component', () => {
  let comp: TarefaRecorrenteExecucaoDetailComponent;
  let fixture: ComponentFixture<TarefaRecorrenteExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaRecorrenteExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaRecorrenteExecucaoDetailComponent,
              resolve: { tarefaRecorrenteExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaRecorrenteExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaRecorrenteExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaRecorrenteExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaRecorrenteExecucaoDetailComponent);

      // THEN
      expect(instance.tarefaRecorrenteExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
