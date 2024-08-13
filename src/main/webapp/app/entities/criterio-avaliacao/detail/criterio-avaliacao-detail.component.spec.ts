import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CriterioAvaliacaoDetailComponent } from './criterio-avaliacao-detail.component';

describe('CriterioAvaliacao Management Detail Component', () => {
  let comp: CriterioAvaliacaoDetailComponent;
  let fixture: ComponentFixture<CriterioAvaliacaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriterioAvaliacaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CriterioAvaliacaoDetailComponent,
              resolve: { criterioAvaliacao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CriterioAvaliacaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CriterioAvaliacaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load criterioAvaliacao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CriterioAvaliacaoDetailComponent);

      // THEN
      expect(instance.criterioAvaliacao()).toEqual(expect.objectContaining({ id: 123 }));
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
