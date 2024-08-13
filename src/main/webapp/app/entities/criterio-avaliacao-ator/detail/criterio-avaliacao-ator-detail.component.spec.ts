import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CriterioAvaliacaoAtorDetailComponent } from './criterio-avaliacao-ator-detail.component';

describe('CriterioAvaliacaoAtor Management Detail Component', () => {
  let comp: CriterioAvaliacaoAtorDetailComponent;
  let fixture: ComponentFixture<CriterioAvaliacaoAtorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriterioAvaliacaoAtorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CriterioAvaliacaoAtorDetailComponent,
              resolve: { criterioAvaliacaoAtor: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CriterioAvaliacaoAtorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CriterioAvaliacaoAtorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load criterioAvaliacaoAtor on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CriterioAvaliacaoAtorDetailComponent);

      // THEN
      expect(instance.criterioAvaliacaoAtor()).toEqual(expect.objectContaining({ id: 123 }));
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
