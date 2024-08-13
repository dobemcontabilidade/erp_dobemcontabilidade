import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaRecorrenteDetailComponent } from './tarefa-recorrente-detail.component';

describe('TarefaRecorrente Management Detail Component', () => {
  let comp: TarefaRecorrenteDetailComponent;
  let fixture: ComponentFixture<TarefaRecorrenteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaRecorrenteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaRecorrenteDetailComponent,
              resolve: { tarefaRecorrente: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaRecorrenteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaRecorrenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefaRecorrente on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaRecorrenteDetailComponent);

      // THEN
      expect(instance.tarefaRecorrente()).toEqual(expect.objectContaining({ id: 123 }));
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
