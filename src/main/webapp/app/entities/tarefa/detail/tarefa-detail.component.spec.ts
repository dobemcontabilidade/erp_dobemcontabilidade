import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TarefaDetailComponent } from './tarefa-detail.component';

describe('Tarefa Management Detail Component', () => {
  let comp: TarefaDetailComponent;
  let fixture: ComponentFixture<TarefaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TarefaDetailComponent,
              resolve: { tarefa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TarefaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarefaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tarefa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TarefaDetailComponent);

      // THEN
      expect(instance.tarefa()).toEqual(expect.objectContaining({ id: 123 }));
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
