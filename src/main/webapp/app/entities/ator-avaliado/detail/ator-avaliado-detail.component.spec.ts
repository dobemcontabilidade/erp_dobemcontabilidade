import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AtorAvaliadoDetailComponent } from './ator-avaliado-detail.component';

describe('AtorAvaliado Management Detail Component', () => {
  let comp: AtorAvaliadoDetailComponent;
  let fixture: ComponentFixture<AtorAvaliadoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtorAvaliadoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AtorAvaliadoDetailComponent,
              resolve: { atorAvaliado: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AtorAvaliadoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AtorAvaliadoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load atorAvaliado on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AtorAvaliadoDetailComponent);

      // THEN
      expect(instance.atorAvaliado()).toEqual(expect.objectContaining({ id: 123 }));
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
